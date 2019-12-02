<?php

namespace App\Http\Controllers\Api;

use App\Http\Controllers\Controller;
use App\Http\Requests\ClientRequest;
use App\Http\Requests\User\RegisterRequest;
use App\Http\Resources\Client\ClientResource as AppClientResource;
use App\Http\Resources\ClientResource;

use App\Http\Resources\UsersResource;
use Symfony\Component\HttpFoundation\Response;
use App\Repositories\Client\Interfaces\ClientRepositoryInterface;
use App\Repositories\User\Interfaces\UserRepositoryInterface;

use Illuminate\Http\Request;
use Illuminate\Support\Facades\Validator;

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
    public function store(Request $request)
    {
       $client=$this->client_repository->create($request->all());
         
        $request['usereable_id']=$client->id;
        $request['usereable_type']='App\\Models\\Client'; 

        return $this->user_repository->register($request->all());
        // $this->user_repository->register($request->all());
        //return $client;

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

    public function getContrats($client)
    {
        return new AppClientResource($this->client_repository->getById($client));
    }

    /**
     * Update the specified resource in storage.
     *
     * @param  App\Http\Requests\ClientRequest  $request
     * @param  int  $client
     * @return \Illuminate\Http\Response
     */
    public function update(RegisterRequest $request, $client)
    {
        $client=$this->client_repository->update($client,$request->all());
        $request['usereable_id']=$client->id;
        $request['usereable_type']='App\\Models\\Client'; 

        return $this->user_repository->update($client->user->id,$request->all());
    }

    /**
     * Remove the specified resource from storage.
     *
     * @param  int  $client
     * @return \Illuminate\Http\Response
     */
    public function destroy($client)
    {
        $this->user_repository->delete($this->client_repository->getById($client)->user->id);
        $this->client_repository->delete($client);
        return response()->json([
            "status" => " success",
            "message" => "Le client a bien été supprimer"
        ],Response::HTTP_NO_CONTENT);
    }

}

