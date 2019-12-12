<?php

namespace App\Http\Controllers\Api;

use App\Http\Controllers\Controller;
use App\Http\Resources\Client\ContratResources;
use App\Http\Resources\ClientsResource;
use App\Services\Contract\ServiceInterface\ClientServiceInterface;
use Illuminate\Http\Request;
use Symfony\Component\HttpFoundation\Response;

class ClientController extends Controller
{
    protected $clientService;

    public function __construct(ClientServiceInterface $clientService)
    {
        $this->clientService = $clientService;
    } 

    /**
     * Display a listing of the resource.
     *
     * @return \Illuminate\Http\Response
     */
    public function index()
    {
        $clientData=$this->clientService->index();
        if(count($clientData)>0){
            return ClientsResource::collection($clientData);
        }else{
            return response()->json([ 'success' => ['message' => 'Aucun client' ]], Response::HTTP_NOT_FOUND);
        }
    }

    /**
     * Store a newly created resource in storage.
     *
     * @param  int $client
     * @return \Illuminate\Http\Response
     */
    public function getContrats($client)
    {
        $contratsData=$this->clientService->getContrats($client);

        if(count($contratsData)>0){
            return ContratResources::collection($contratsData);
        }else{
            return response()->json([ 'success' => ['message' => 'Aucun contrat pour le client' ]], Response::HTTP_NOT_FOUND);
        }
        
    }

    /**
     * Store a newly created resource in storage.
     *
     * @param  int $client
     * @return \Illuminate\Http\Response
     */
    public function getLastContrats($client)
    {
        $lastContratsData=$this->clientService->getLastContrats($client)/* ->where('userable_type','App\\Models\\Marchand') */;
        if(count($lastContratsData)>0){
            return ContratResources::collection($lastContratsData);
        }else{
            return response()->json([ 'success' => ['message' => 'Aucun contrat pour le client' ]], Response::HTTP_NOT_FOUND);
        }
    }

    /**
     * Display the specified resource.
     *
     * @param  int  $id
     * @return \Illuminate\Http\Response
     */
    public function show($client)
    {
        $clientsData=$this->clientService->read($client);
        if($clientsData){
            return response()->json([ 'success' => ['data' => new ClientsResource($clientsData) ]], Response::HTTP_OK);
        }else{
            return response()->json([ 'success' => ['message' => 'Aucun client' ]], Response::HTTP_NOT_FOUND);
        }
    }

    public function store(Request $request)
    {
        try {         
            return response()->json([ 'success' => ['data' => new ClientsResource( $this->clientService->create($request)) ]], Response::HTTP_OK);
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


    public function update(Request $request, $client){
        
        try {         
            if($this->clientService->update($request,$client)){
                return $this->show($client);
            }else{
                return response()->json([ 'errors' => ['message' =>  'Le client n\'a pas été mis à jour'  ]], Response::HTTP_INTERNAL_SERVER_ERROR);
            }
        } catch (\Exception $e) {
            $message = $e->getMessage();
            return response()->json(['errors' => [ 'message' => $message]],Response::HTTP_INTERNAL_SERVER_ERROR);
        }
    }

    public function destroy($client){
        try {       
            if($this->clientService->delete($client)){
                return response()->json([ 'success' => ['message' => 'Le client à bien été supprimé' ]], Response::HTTP_OK);
            }else{
                return response()->json([ 'errors' => ['message' =>  'Le client n\'a pas été supprimé'  ]], Response::HTTP_INTERNAL_SERVER_ERROR);
            }  
        } catch (\Exception $e) {
            $message = $e->getMessage();
            return response()->json(['errors' => [ 'message' => $message]],Response::HTTP_INTERNAL_SERVER_ERROR);
        }
    }
}
