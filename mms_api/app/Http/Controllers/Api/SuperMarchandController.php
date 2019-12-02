<?php

namespace App\Http\Controllers\Api;

use Illuminate\Http\Request;
use App\Http\Controllers\Controller;
use App\Http\Requests\SuperMarchandRequest;
use App\Repositories\SuperMarchand\Interfaces\SuperMarchandRepositoryInterface;

class SuperMarchandController extends Controller
{

    protected $user_repository;
    protected $supermarchand_repository;
    public function __construct(SuperMarchandRepositoryInterface $supermarchandRepositoryInterface, UserRepositoryInterface $userRepositoryInterface)
    {
        $this->user_repository=$userRepositoryInterface;    
        $this->supermarchand_repository=$supermarchandRepositoryInterface;    
    }

    /**
     * Display a listing of the resource.
     *
     * @return \Illuminate\Http\Response
     */
    public function index()
    {
        return $this->supermarchand_repository->all();
    }

    /**
     * Store a newly created resource in storage.
     *
     * @param  \Illuminate\Http\Request  $request
     * @return \Illuminate\Http\Response
     */
    public function store( SuperMarchandRequest $request)
    {
        return $this->supermarchand_repository->create($request->all());
    }

    /**
     * Display the specified resource.
     *
     * @param  int  $id
     * @return \Illuminate\Http\Response
     */
    public function show($id)
    {
        return $this->supermarchand_repository->getById($id);
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
        return $this->supermarchand_repository->update($id,$request->all());
    }

    /**
     * Remove the specified resource from storage.
     *
     * @param  int  $id
     * @return \Illuminate\Http\Response
     */
    public function destroy($id)
    {
        return $this->supermarchand_repository->delete($id);
    }
}
