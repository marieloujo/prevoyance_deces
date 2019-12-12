<?php

namespace App\Http\Controllers\Api;

use App\Http\Controllers\Controller;
use App\Http\Resources\CommunesResource;
use App\Http\Resources\Zone\CommuneResources;
use App\Http\Resources\DepartementsResource;
use App\Http\Resources\Zone\UserResource;
use App\Services\Contract\ServiceInterface\DepartementServiceInterface;
use Illuminate\Http\Request;
use Symfony\Component\HttpFoundation\Response;

class DepartementController extends Controller
{

    protected $departementService;

    public function __construct(DepartementServiceInterface $departementService)
    {
        $this->departementService = $departementService;
    } 

    /**
     * Display a listing of the resource.
     *
     * @return \Illuminate\Http\Response
     */
    public function index()
    {
        $departementData=$this->departementService->index();
        if(count($departementData)>0){
            return DepartementsResource::collection($departementData);
        }else{
            return response()->json([ 'success' => ['message' => 'Aucun utilisateur' ]], Response::HTTP_NOT_FOUND);
        }
    }

    public function getCommunes($departement)
    {
        $departementData=$this->departementService->getCommunes($departement);
        if(count($departementData)>0){
            return CommunesResource::collection($departementData);
        }else{
            return response()->json([ 'success' => ['message' => 'Aucune commune' ]], Response::HTTP_NOT_FOUND);
        }
    }

    /**
     * Display the specified resource.
     *
     * @param  int  $id
     * @return \Illuminate\Http\Response
     */
    public function show($departement)
    {
        $departementData=$this->departementService->read($departement);
        if($departementData){
            return response()->json([ 'success' => ['data' => new DepartementsResource($departementData) ]], Response::HTTP_OK);
        }else{
            return response()->json([ 'success' => ['message' => 'Aucun utilisateur trouvé' ]], Response::HTTP_NOT_FOUND);
        }
    }

}
