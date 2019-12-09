<?php

namespace App\Http\Controllers\Api;

use App\Http\Controllers\Controller;
use App\Http\Resources\DirectionsResource;
use App\Services\Contract\ServiceInterface\DirectionServiceInterface;
use Illuminate\Http\Request;
use Symfony\Component\HttpFoundation\Response;

class DirectionController extends Controller
{
    protected $directionService;

    public function __construct(DirectionServiceInterface $directionService)
    {
        $this->directionService = $directionService;
    } 

    /**
     * Display a listing of the resource.
     *
     * @return \Illuminate\Http\Response
     */
    public function index()
    {
        return $directionData=$this->directionService->index();
        if(count($directionData)>0){
            return DirectionsResource::collection($directionData);
        }else{
            return response()->json([ 'success' => ['message' => 'Aucun direction' ]], Response::HTTP_NOT_FOUND);
        }
    }

    /**
     * Display the specified resource.
     *
     * @param  int  $id
     * @return \Illuminate\Http\Response
     */
    public function show($direction)
    {
        $directionData=$this->directionService->read($direction);
        if($directionData){
            return response()->json([ 'success' => ['data' => new DirectionsResource($directionData) ]], Response::HTTP_OK);
        }else{
            return response()->json([ 'success' => ['message' => 'Aucun direction' ]], Response::HTTP_NOT_FOUND);
        }
    }

    public function store(Request $request)
    {
        try {         
            return response()->json([ 'success' => ['data' => new DirectionsResource( $this->directionService->create($request)) ]], Response::HTTP_OK);
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


    public function update(Request $request, $direction){
        
        try {         
            if($this->directionService->update($request,$direction)){
                return $this->show($direction);
            }else{
                return response()->json([ 'errors' => ['message' =>  'Le direction n\'a pas été mis à jour'  ]], Response::HTTP_INTERNAL_SERVER_ERROR);
            }
        } catch (\Exception $e) {
            $message = $e->getMessage();
            return response()->json(['errors' => [ 'message' => $message]],Response::HTTP_INTERNAL_SERVER_ERROR);
        }
    }

    public function destroy($direction){
        try {       
            if($this->directionService->delete($direction)){
                return response()->json([ 'success' => ['message' => 'Le direction à bien été supprimé' ]], Response::HTTP_OK);
            }else{
                return response()->json([ 'errors' => ['message' =>  'Le direction n\'a pas été supprimé'  ]], Response::HTTP_INTERNAL_SERVER_ERROR);
            }  
        } catch (\Exception $e) {
            $message = $e->getMessage();
            return response()->json(['errors' => [ 'message' => $message]],Response::HTTP_INTERNAL_SERVER_ERROR);
        }
    }
}
