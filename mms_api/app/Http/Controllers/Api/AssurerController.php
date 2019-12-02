<?php

namespace App\Http\Controllers\Api;

use Illuminate\Http\Request;
use App\Http\Controllers\Controller;
use App\Http\Requests\User\RegisterRequest;
use App\Http\Requests\ValidationRequest;
use App\Repositories\Assurer\Interfaces\AssurerRepositoryInterface;
use App\Repositories\User\Interfaces\UserRepositoryInterface;

class AssurerController extends Controller
{ 
    protected $user_repository;
    protected $assurer_repository;
    public function __construct(UserRepositoryInterface $userRepositoryInterface,AssurerRepositoryInterface $assurerRepositoryInterface)
    {
        $this->user_repository=$userRepositoryInterface;    
        $this->assurer_repository=$assurerRepositoryInterface;    
    }
    /**
     * Display a listing of the resource.
     *
     * @return \Illuminate\Http\Response
     */
    public function index()
    {
        $this->assurer_repository->all();
    }

    /**
     * Store a newly created resource in storage.
     *
     * @param  \Illuminate\Http\Request  $request
     * @return \Illuminate\Http\Response
     */
    public function store(RegisterRequest $request)
    {
        $assurer=$this->assurer_repository->create($request->all());
        $request['usereable_id']=$assurer->id;
        $request['usereable_type']='App\\Models\\assurer'; 

        return $this->user_repository->create($assurer->user->id,$request->all());
    }

    /**
     * Display the specified resource.
     *
     * @param  int  $id
     * @return \Illuminate\Http\Response
     */
    public function show($id)
    {
        $this->assurer_repository->getById($id);
    }

    /**
     * Update the specified resource in storage.
     *
     * @param  \Illuminate\Http\Request  $request
     * @param  int  $id
     * @return \Illuminate\Http\Response
     */
    public function update(ValidationRequest $request, $id)
    {
        $this->assurer_repository->update($id,$request->all());
    }

    /**
     * Remove the specified resource from storage.
     *
     * @param  int  $id
     * @return \Illuminate\Http\Response
     */
    public function destroy($id)
    {
        $this->user_repository->delete($this->assurer_repository->getById($id)->user->id);
        $this->assurer_repository->delete($id);

        return response()->json([
            "status" => " success",
            "message" => "Le client a bien été supprimer"
        ],Response::HTTP_NO_CONTENT);
    }
}
