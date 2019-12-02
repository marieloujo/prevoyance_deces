<?php

namespace App\Http\Controllers\Api;

use Illuminate\Http\Request;
use App\Http\Controllers\Controller;
use App\Http\Requests\BeneficeRequest;
use App\Repositories\Benefice\Interfaces\BeneficeRepositoryInterface;

class BeneficeController extends Controller
{
    protected $benefice_repository;
    
    public function __construct(BeneficeRepositoryInterface $beneficeRepositoryInterface)
    {    
        $this->benefice_repository=$beneficeRepositoryInterface;  
    }
    /**
     * Display a listing of the resource.
     *
     * @return \Illuminate\Http\Response
     */
    public function index()
    {
        return $this->benefice_repository->all();  
        //
    }

    /**
     * Store a newly created resource in storage.
     *
     * @param  \Illuminate\Http\Request  $request
     * @return \Illuminate\Http\Response
     */
    public function store(BeneficeRequest $request)
    {
        return $this->benefice_repository->create($request->all());
    }

    /**
     * Display the specified resource.
     *
     * @param  int  $id
     * @return \Illuminate\Http\Response
     */
    public function show($id)
    {
        return $this->benefice_repository->getById($id);  
    }

    /**
     * Update the specified resource in storage.
     *
     * @param  \Illuminate\Http\Request  $request
     * @param  int  $id
     * @return \Illuminate\Http\Response
     */
    public function update(BeneficeRequest $request, $id)
    {
        return $this->benefice_repository->update($id,$request->all());
    }

    /**
     * Remove the specified resource from storage.
     *
     * @param  int  $id
     * @return \Illuminate\Http\Response
     */
    public function destroy($id)
    {
        return $this->benefice_repository->delete($id);
    }
}
