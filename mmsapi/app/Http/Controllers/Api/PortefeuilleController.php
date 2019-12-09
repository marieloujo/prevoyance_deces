<?php

namespace App\Http\Controllers\Api;

use App\Http\Controllers\Controller;
use App\Http\Resources\PortefeuillesResource;
use App\Services\Contract\ServiceInterface\PortefeuilleServiceInterface;
use Illuminate\Http\Request;
use Symfony\Component\HttpFoundation\Response;

class PortefeuilleController extends Controller
{
    protected $portefeuilleService;

    public function __construct(PortefeuilleServiceInterface $portefeuilleService)
    {
        $this->portefeuilleService = $portefeuilleService;
    } 

    /**
     * Display a listing of the resource.
     *
     * @return \Illuminate\Http\Response
     */
    public function index()
    {
        return $portefeuilleData=$this->portefeuilleService->index();
        if(count($portefeuilleData)>0){
            return PortefeuillesResource::collection($portefeuilleData);
        }else{
            return response()->json([ 'success' => ['message' => 'Aucun portefeuille' ]], Response::HTTP_NOT_FOUND);
        }
    }

    /**
     * Display the specified resource.
     *
     * @param  int  $id
     * @return \Illuminate\Http\Response
     */
    public function show($portefeuille)
    {
        $portefeuilleData=$this->portefeuilleService->read($portefeuille);
        if($portefeuilleData){
            return response()->json([ 'success' => ['data' => new PortefeuillesResource($portefeuilleData) ]], Response::HTTP_OK);
        }else{
            return response()->json([ 'success' => ['message' => 'Aucun portefeuille' ]], Response::HTTP_NOT_FOUND);
        }
    }

    public function store(Request $request)
    {
        return $this->portefeuilleService->create($request);
        try {         
            return response()->json([ 'success' => ['data' => new PortefeuillesResource( $this->portefeuilleService->create($request)) ]], Response::HTTP_OK);
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


    public function update(Request $request, $portefeuille){
        
        try {         
            if($this->portefeuilleService->update($request,$portefeuille)){
                return $this->show($portefeuille);
            }else{
                return response()->json([ 'errors' => ['message' =>  'Le portefeuille n\'a pas été mis à jour'  ]], Response::HTTP_INTERNAL_SERVER_ERROR);
            }
        } catch (\Exception $e) {
            $message = $e->getMessage();
            return response()->json(['errors' => [ 'message' => $message]],Response::HTTP_INTERNAL_SERVER_ERROR);
        }
    }

    public function destroy($portefeuille){
        try {       
            if($this->portefeuilleService->delete($portefeuille)){
                return response()->json([ 'success' => ['message' => 'Le portefeuille à bien été supprimé' ]], Response::HTTP_OK);
            }else{
                return response()->json([ 'errors' => ['message' =>  'Le portefeuille n\'a pas été supprimé'  ]], Response::HTTP_INTERNAL_SERVER_ERROR);
            }  
        } catch (\Exception $e) {
            $message = $e->getMessage();
            return response()->json(['errors' => [ 'message' => $message]],Response::HTTP_INTERNAL_SERVER_ERROR);
        }
    }
}
