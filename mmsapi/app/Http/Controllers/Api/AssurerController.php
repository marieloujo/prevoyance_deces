<?php

namespace App\Http\Controllers\Api;

use App\Http\Controllers\Controller;
use App\Http\Resources\AssuresResource;
use App\Services\Contract\ServiceInterface\AssurerServiceInterface;
use Illuminate\Http\Request;
use Symfony\Component\HttpFoundation\Response;

class AssurerController extends Controller
{ 
    protected $assurerService;

    public function __construct(AssurerServiceInterface $assurerService)
    {
        $this->assurerService = $assurerService;
    } 

    /**
     * Display a listing of the resource.
     *
     * @return \Illuminate\Http\Response
     */
    public function index()
    {
        $assurerData=$this->assurerService->index();
        if(count($assurerData)>0){
            return AssuresResource::collection($assurerData);
        }else{
            return response()->json([ 'success' => ['message' => 'Aucun assurer' ]], Response::HTTP_NOT_FOUND);
        }
    }

    /**
     * Display the specified resource.
     *
     * @param  int  $id
     * @return \Illuminate\Http\Response
     */
    public function show($assurer)
    {
        $assurerData=$this->assurerService->read($assurer);
        if($assurerData){
            return response()->json([ 'success' => ['data' => new AssuresResource($assurerData) ]], Response::HTTP_OK);
        }else{
            return response()->json([ 'success' => ['message' => 'Aucun assurer' ]], Response::HTTP_NOT_FOUND);
        }
    }

    public function store(Request $request)
    {
        try {         
            return response()->json([ 'success' => ['data' => new AssuresResource( $this->assurerService->create($request)) ]], Response::HTTP_OK);
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


    public function update(Request $request, $assurer){
        
        try {         
            if($this->assurerService->update($request,$assurer)){
                return $this->show($assurer);
            }else{
                return response()->json([ 'errors' => ['message' =>  'Le assurer n\'a pas été mis à jour'  ]], Response::HTTP_INTERNAL_SERVER_ERROR);
            }
        } catch (\Exception $e) {
            $message = $e->getMessage();
            return response()->json(['errors' => [ 'message' => $message]],Response::HTTP_INTERNAL_SERVER_ERROR);
        }
    }

    public function destroy($assurer){
        try {       
            if($this->assurerService->delete($assurer)){
                return response()->json([ 'success' => ['message' => 'Le assurer à bien été supprimé' ]], Response::HTTP_OK);
            }else{
                return response()->json([ 'errors' => ['message' =>  'Le assurer n\'a pas été supprimé'  ]], Response::HTTP_INTERNAL_SERVER_ERROR);
            }  
        } catch (\Exception $e) {
            $message = $e->getMessage();
            return response()->json(['errors' => [ 'message' => $message]],Response::HTTP_INTERNAL_SERVER_ERROR);
        }
    }
}
