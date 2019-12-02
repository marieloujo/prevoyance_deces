<?php

namespace App\Http\Controllers\Api;

use Illuminate\Http\Request;
use App\Http\Controllers\Controller;
use App\Repositories\Compte\Interfaces\CompteRepositoryInterface;
use Carbon\Carbon;
use Illuminate\Support\Facades\Auth;

class CompteController extends Controller
{
    protected $compte_repository;
    
    public function __construct(CompteRepositoryInterface $compteRepositoryInterface)
    {    
        $this->compte_repository=$compteRepositoryInterface;  
    }
    /**
     * Display a listing of the resource.
     *
     * @return \Illuminate\Http\Response
     */
    public function index()
    {
        $this->compte_repository->all();
    }

    /**
     * Store a newly created resource in storage.
     *
     * @param  \Illuminate\Http\Request  $request
     * @return \Illuminate\Http\Response
     */
    public function store(Request $request)
    {
        return $this->compte_repository->create($request->all());
    }

    /**
     * Display the specified resource.
     *
     * @param  int  $id
     * @return \Illuminate\Http\Response
     */
    public function show($id)
    {
        return $this->compte_repository->getById($id);
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
        return $this->compte_repository->update($id,$request->all());
    }

    /**
     * Remove the specified resource from storage.
     *
     * @param  int  $id
     * @return \Illuminate\Http\Response
     */
    public function destroy($id)
    {
        return $this->compte_repository->delete($id);
    }

    public function getCompte()
    {
        return $this->compte_repository->getCompte()->montant;
    }

    public function getCompteTime($end)
    {
        $end=Carbon::now();
        return $this->compte_repository->getCompteTime($end);
    }

}
