<?php

namespace App\Http\Controllers\Api;

use App\Http\Controllers\Controller;
use App\Http\Requests\ClientRequest;
use App\Http\Resources\ClientResources;
use App\Http\Resources\Souscripteur\ClientResource;
use App\Http\Resources\UserResources;

use App\Repositories\Client\Interfaces\ClientRepositoryInterface;
use App\Repositories\User\Interfaces\UserRepositoryInterface;

use Illuminate\Http\Request;

class ClientController extends Controller
{
    protected $user_repository;
    protected $client_repository;
    public function __construct(UserRepositoryInterface $userRepositoryInterface, ClientRepositoryInterface $clientRepositoryInterface)
    {
        $this->user_repository=$userRepositoryInterface;    
        $this->client_repository=$clientRepositoryInterface;    
    }
/**
     * Display a listing of the resource.
     *
     * @return \Illuminate\Http\Response
     */
    public function index()
    {
        return ClientResource::collection( $this->client_repository->all());
    }

    /**
     * Store a newly created resource in storage.
     *
     * @param  App\Http\Requests\ClientRequest  $request
     * @return \Illuminate\Http\Response
     */
    public function store(ClientRequest $request)
    {
        $client=$this->client_repository->create($request->all());
       return $this->user_repository->register($request->request->all(),true,false,$client->id,'App\\Models\\Client');
    }

    /**
     * Display the specified resource.
     *
     * @param  int  $client
     * @return \Illuminate\Http\Response
     */
    public function show($client)
    {
        return new ClientResource($this->client_repository->getById($client));
    }


    /**
     * Display the specified resource.
     *
     * @param  int  $client
     * @return \Illuminate\Http\Response
     */
    public function contrats()
    {   
        ClientResource::collection($this->user_repository->getAuth()->usereable);
    }
    
    public function showContrat()
    {   
        new ClientResource($this->user_repository->getAuth()->usereable);
    }

    /**
     * Update the specified resource in storage.
     *
     * @param  App\Http\Requests\ClientRequest  $request
     * @param  int  $client
     * @return \Illuminate\Http\Response
     */
    public function update(ClientRequest $request, $client)
    {
        //
    }

    /**
     * Remove the specified resource from storage.
     *
     * @param  int  $client
     * @return \Illuminate\Http\Response
     */
    public function destroy($client)
    {
        $this->client_repository->delete($client);
        
        //$this->client_images_repository->deleteT($client);

        return response()->json([
            "status" => " success",
            "message" => "Le client a bien été supprimer"
        ],Response::HTTP_NO_CONTENT);
    }

}
