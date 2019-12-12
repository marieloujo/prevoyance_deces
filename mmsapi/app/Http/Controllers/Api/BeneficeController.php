<?php

namespace App\Http\Controllers\Api;

use App\Http\Controllers\Controller;
use App\Http\Resources\BeneficesResource;
use App\Services\Contract\ServiceInterface\BeneficeServiceInterface;
use Illuminate\Http\Request;
use Symfony\Component\HttpFoundation\Response;

class BeneficeController extends Controller
{
    protected $beneficeService;

    public function __construct(BeneficeServiceInterface $beneficeService)
    {
        $this->beneficeService = $beneficeService;
    } 

    /**
     * Display a listing of the resource.
     *
     * @return \Illuminate\Http\Response
     */
    public function index()
    {
        return $beneficeData=$this->beneficeService->index();
        if(count($beneficeData)>0){
            return BeneficesResource::collection($beneficeData);
        }else{
            return response()->json([ 'success' => ['message' => 'Aucun benefice' ]], Response::HTTP_NOT_FOUND);
        }
    }

    /**
     * Display the specified resource.
     *
     * @param  int  $id
     * @return \Illuminate\Http\Response
     */
    public function show($benefice)
    {
        $beneficeData=$this->beneficeService->read($benefice);
        if($beneficeData){
            return response()->json([ 'success' => ['data' => new BeneficesResource($beneficeData) ]], Response::HTTP_OK);
        }else{
            return response()->json([ 'success' => ['message' => 'Aucun benefice' ]], Response::HTTP_NOT_FOUND);
        }
    }

    public function store(Request $request)
    {
        try {         
            return response()->json([ 'success' => ['data' => new BeneficesResource( $this->beneficeService->create($request)) ]], Response::HTTP_OK);
        } catch (\Exception $e) {
            $message = $e->getMessage();
            return response()->json(['errors' => [ 'message' => $message]],Response::HTTP_NOT_FOUND);
        }
    }

    /**
     * Update the specified resource in storage.
     *
     * @param  \Illuminate\Http\Request  $request
     * @param  int  $id
     * @return \Illuminate\Http\Response
     */


    public function update(Request $request, $benefice){
        
        try {         
            if($this->beneficeService->update($request,$benefice)){
                return $this->show($benefice);
            }else{
                return response()->json([ 'errors' => ['message' =>  'Le benefice n\'a pas été mis à jour'  ]], Response::HTTP_INTERNAL_SERVER_ERROR);
            }
        } catch (\Exception $e) {
            $message = $e->getMessage();
            return response()->json(['errors' => [ 'message' => $message]],Response::HTTP_INTERNAL_SERVER_ERROR);
        }
    }

    public function destroy($benefice){
        try {       
            if($this->beneficeService->delete($benefice)){
                return response()->json([ 'success' => ['message' => 'Le benefice à bien été supprimé' ]], Response::HTTP_OK);
            }else{
                return response()->json([ 'errors' => ['message' =>  'Le benefice n\'a pas été supprimé'  ]], Response::HTTP_INTERNAL_SERVER_ERROR);
            }  
        } catch (\Exception $e) {
            $message = $e->getMessage();
            return response()->json(['errors' => [ 'message' => $message]],Response::HTTP_INTERNAL_SERVER_ERROR);
        }
    }
}
