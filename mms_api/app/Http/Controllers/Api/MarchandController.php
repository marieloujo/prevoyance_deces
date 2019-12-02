<?php

namespace App\Http\Controllers\Api;

use Illuminate\Http\Request;
use App\Http\Controllers\Controller;
use App\Http\Requests\MarchandRequest;
use App\Http\Resources\Marchand\MarchandResource;
use App\Http\Resources\Departement\UsersDepartementResource;
use App\Http\Resources\MarchandResources;

use App\Http\Resources\Zone\UsersCommuneResource;
use App\Repositories\Marchand\Interfaces\MarchandRepositoryInterface;
use App\Repositories\Client\Interfaces\ClientRepositoryInterface;
use App\Repositories\Portefeuille\Interfaces\PortefeuilleRepositoryInterface;
use App\Repositories\User\Interfaces\UserRepositoryInterface;
use Illuminate\Support\Carbon;

class MarchandController extends Controller
{
    protected $user_repository;
    protected $portefeuille_repository;
    protected $marchand_repository;
    public function __construct(MarchandRepositoryInterface $marchandRepositoryInterface, UserRepositoryInterface $userRepositoryInterface, PortefeuilleRepositoryInterface $portefeuilleRepositoryInterface)
    {
        $this->user_repository=$userRepositoryInterface;    
        $this->portefeuille_repository=$portefeuilleRepositoryInterface; 
        $this->marchand_repository=$marchandRepositoryInterface;    
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
    public function store(MarchandRequest $request)
    {
        return $this->marchand_repository->create($request->all());
    }

    /**
     * Display the specified resource.
     *
     * @param  int  $id
     * @return \Illuminate\Http\Response
     */
    public function show($id)
    {
        return $this->marchand_repository->getById($id);
    }

    /**
     * Display the specified resource.
     *
     * @param  int  $id
     * @return \Illuminate\Http\Response
     */
    public function showByCommune()
    {
        return new UsersDepartementResource($this->user_repository->getAuth()->commune->departement);
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
        return $this->marchand_repository->update($id,$request->all());
    }

    /**
     * Remove the specified resource from storage.
     *
     * @param  int  $id
     * @return \Illuminate\Http\Response
     */
    public function destroy($id)
    {
        return $this->marchand_repository->destroy($id);
    }

    public function getContrats($marchand)
    {
        return new MarchandResource($this->marchand_repository->getById($marchand));
    }
    public function getTransaction($contrat,$date)
    { return $this->portefeuille_repository->getById($contrat,$date);
        //return new MarchandResource($this->marchand_repository->getById($marchand));
    }
}
