<?php

namespace App\Http\Controllers\Api;

use Illuminate\Http\Request;
use App\Http\Controllers\Controller;
use App\Http\Requests\User\RegisterRequest;
use App\Repositories\Beneficiaire\Interfaces\BeneficiaireRepositoryInterface;

class BeneficiaireController extends Controller
{
    protected $user_repository;
    protected $beneficiaire_repository;
    public function __construct(BeneficiaireRepositoryInterface $beneficiaireRepositoryInterface, UserRepositoryInterface $userRepositoryInterface)
    {
        $this->user_repository=$userRepositoryInterface;    
        $this->beneficiaire_repository=$beneficiaireRepositoryInterface;    
    }

    /**
     * Display a listing of the resource.
     *
     * @return \Illuminate\Http\Response
     */
    public function index()
    {
        return $this->beneficiaire_repository->all();
    }

    /**
     * Store a newly created resource in storage.
     *
     * @param  \Illuminate\Http\Request  $request
     * @return \Illuminate\Http\Response
     */
    public function store(RegisterRequest $request)
    {
        $beneficiaire= $this->beneficiaire_repository->create($request->all());
        $request['usereable_id']=$beneficiaire->id;
        $request['usereable_type']='App\\Models\\Beneficiaire'; 

        return $this->user_repository->create($request->all());
    }

    /**
     * Display the specified resource.
     *
     * @param  int  $id
     * @return \Illuminate\Http\Response
     */
    public function show($id)
    {
        return $this->beneficiaire_repository->getById($id);
    }

    /**
     * Update the specified resource in storage.
     *
     * @param  \Illuminate\Http\Request  $request
     * @param  int  $id
     * @return \Illuminate\Http\Response
     */
    public function update(RegisterRequest $request, $id)
    {
        $beneficiaire= $this->beneficiaire_repository->update($id,$request->all());
        $request['usereable_id']=$beneficiaire->id;
        $request['usereable_type']='App\\Models\\Beneficiaire'; 

        return $this->user_repository->update($beneficiaire->user->id,$request->all());
    }

    /**
     * Remove the specified resource from storage.
     *
     * @param  int  $id
     * @return \Illuminate\Http\Response
     */
    public function destroy($id)
    {
        $this->user_repository->delete($this->beneficiaire_repository->getById($id)->user->id);
        $this->beneficiaire_repository->delete($id);

        return response()->json([
            "status" => " success",
            "message" => "Le client a bien été supprimer"
        ],Response::HTTP_NO_CONTENT);
    }
}
