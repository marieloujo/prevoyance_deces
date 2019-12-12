<?php

namespace App\Http\Controllers\Api;

use App\Http\Controllers\Controller;
use App\Http\Resources\CommunesResource;
use App\Http\Resources\DepartementsResource;
use App\Http\Resources\UserResources;
use App\Http\Resources\Zone\UserResource;
use App\Services\Contract\ServiceInterface\CommuneServiceInterface;
use Illuminate\Http\Request;
use Symfony\Component\HttpFoundation\Response;

class CommuneController extends Controller
{

    protected $communeService;

    public function __construct(CommuneServiceInterface $communeService)
    {
        $this->communeService = $communeService;
    } 

    /**
     * Display a listing of the resource.
     *
     * @return \Illuminate\Http\Response
     */
    public function index()
    {
        $communeData=$this->communeService->index();
        if(count($communeData)>0){
            return CommunesResource::collection($communeData);
        }else{
            return response()->json([ 'success' => ['message' => 'Aucune commune' ]], Response::HTTP_NOT_FOUND);
        }
    }

    /**
     * Store a newly created resource in storage.
     *
     * @param  int $commune
     * @return \Illuminate\Http\Response
     */
    public function getUsers($commune)
    {
        $usersData=$this->communeService->getUsers($commune);
        if(count($usersData)>0){
            return UserResources::collection($usersData);
        }else{
            return response()->json([ 'success' => ['message' => 'Aucun utilisateur dans la commune' ]], Response::HTTP_NOT_FOUND);
        }
    }

    /**
     * Store a newly created resource in storage.
     *
     * @param  int $commune
     * @return \Illuminate\Http\Response
     */
    public function getMarchands($commune)
    {
        $marchandsData=$this->communeService->getUsers($commune)->where('userable_type','App\\Models\\Marchand');
        if(count($marchandsData)>0){
            return UserResource::collection($marchandsData);
        }else{
            return response()->json([ 'success' => ['message' => 'Aucun marchand dans la commune' ]], Response::HTTP_NOT_FOUND);
        }
    }

    public function getDepartement($commune)
    {
        $departementData=$this->communeService->getDepartement($commune);
        if($departementData){
            return response()->json([ 'success' => ['data' => new DepartementsResource($departementData) ]], Response::HTTP_OK);
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
    public function show($commune)
    {
        $communeData=$this->communeService->read($commune);
        if($communeData){
            return response()->json([ 'success' => ['data' => new CommunesResource($communeData) ]], Response::HTTP_OK);
        }else{
            return response()->json([ 'success' => ['message' => 'Aucune commune' ]], Response::HTTP_NOT_FOUND);
        }
    }


}
