<?php

namespace App\Http\Controllers\Api;

use App\Http\Controllers\Controller;
use App\Http\Resources\ComptesResource;
use App\Services\Contract\ServiceInterface\CompteServiceInterface;
use Illuminate\Http\Request;
use Symfony\Component\HttpFoundation\Response;

class CompteController extends Controller
{
    protected $compteService;

    public function __construct(CompteServiceInterface $compteService)
    {
        $this->compteService = $compteService;
    } 

    /**
     * Display a listing of the resource.
     *
     * @return \Illuminate\Http\Response
     */
    public function index()
    {
        $compteData=$this->compteService->index();
        if(count($compteData)>0){
            return ComptesResource::collection($compteData);
        }else{
            return response()->json([ 'success' => ['message' => 'Aucun compte' ]], Response::HTTP_NOT_FOUND);
        }
    }

    /**
     * Display the specified resource.
     *
     * @param  int  $id
     * @return \Illuminate\Http\Response
     */
    public function show($compte)
    {
        $comptesData=$this->compteService->read($compte);
        if($comptesData){
            return response()->json([ 'success' => ['data' => new ComptesResource($comptesData) ]], Response::HTTP_OK);
        }else{
            return response()->json([ 'success' => ['message' => 'Aucun compte' ]], Response::HTTP_NOT_FOUND);
        }
    }

    public function store(Request $request)
    {
        try {         
            return response()->json([ 'success' => ['data' => new ComptesResource( $this->compteService->create($request)) ]], Response::HTTP_OK);
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


    public function update(Request $request, $compte){
        
        try {         
            if($this->compteService->update($request,$compte)){
                return $this->show($compte);
            }else{
                return response()->json([ 'errors' => ['message' =>  'Le compte n\'a pas été mis à jour'  ]], Response::HTTP_INTERNAL_SERVER_ERROR);
            }
        } catch (\Exception $e) {
            $message = $e->getMessage();
            return response()->json(['errors' => [ 'message' => $message]],Response::HTTP_INTERNAL_SERVER_ERROR);
        }
    }

    public function destroy($compte){
        try {       
            if($this->compteService->delete($compte)){
                return response()->json([ 'success' => ['message' => 'Le compte à bien été supprimé' ]], Response::HTTP_OK);
            }else{
                return response()->json([ 'errors' => ['message' =>  'Le compte n\'a pas pu être supprimé'  ]], Response::HTTP_INTERNAL_SERVER_ERROR);
            }  
        } catch (\Exception $e) {
            $message = $e->getMessage();
            return response()->json(['errors' => [ 'message' => $message]],Response::HTTP_INTERNAL_SERVER_ERROR);
        }
    }
}
