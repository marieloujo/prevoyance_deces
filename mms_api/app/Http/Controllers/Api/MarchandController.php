<?php

namespace App\Http\Controllers\Api;

use Illuminate\Http\Request;
use App\Http\Controllers\Controller;
use App\Http\Resources\Marchand\MarchandResource;
use App\Http\Resources\MarchandResources;

use App\Http\Resources\Zone\UsersCommuneResource;
use App\Repositories\Marchand\Interfaces\MarchandRepositoryInterface;
use App\Repositories\Client\Interfaces\ClientRepositoryInterface;
use App\Repositories\User\Interfaces\UserRepositoryInterface;
class MarchandController extends Controller
{
    protected $user_repository;
    protected $client_repository;
    protected $marchand_repository;
    public function __construct(/* MarchandRepositoryInterface $marchandRepositoryInterface, */UserRepositoryInterface $userRepositoryInterface, ClientRepositoryInterface $clientRepositoryInterface)
    {
        $this->user_repository=$userRepositoryInterface;    
        $this->client_repository=$clientRepositoryInterface; 
       // $this->marchand_repository=$marchandRepositoryInterface;    
    }
/**
     * Display a listing of the resource.
     *
     * @return \Illuminate\Http\Response
     */
    public function index()
    {
        return MarchandResource::collection( $this->marchand_repository->all());
    }

    /**
     * Store a newly created resource in storage.
     *
     * @param  \Illuminate\Http\Request  $request
     * @return \Illuminate\Http\Response
     */
    public function store(Request $request)
    {
        //
    }

    /**
     * Display the specified resource.
     *
     * @param  int  $id
     * @return \Illuminate\Http\Response
     */
    public function show($id)
    {
        //
    }

    /**
     * Display the specified resource.
     *
     * @param  int  $id
     * @return \Illuminate\Http\Response
     */
    public function showByCommune()
    {
        return UsersCommuneResource::collection($this->user_repository->getAuth()->commune->departement->communes);
    }

    /**
     * Update the specified resource in storage.
     *
     * @param  \Illuminate\Http\Request  $request
     * @param  int  $id
     * @return \Illuminate\Http\Response
     */
    public function update(Request $request, $id)
    {
        //
    }

    /**
     * Remove the specified resource from storage.
     *
     * @param  int  $id
     * @return \Illuminate\Http\Response
     */
    public function destroy($id)
    {
        //
    }
}
