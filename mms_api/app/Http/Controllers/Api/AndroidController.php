<?php

namespace App\Http\Controllers\Api;

use App\Http\Controllers\Controller;
use App\Http\Resources\ClientResources\ClientResource;
use App\Http\Resources\MarchandResources\MarchandResource;
use App\Http\Resources\SuperMarchandResources\SuperMarchandResource;
use App\Models\Client;
use App\Models\Marchand;
use App\Models\SuperMarchand;
use App\User;
use Illuminate\Http\Request;

class AndroidController extends Controller
{

    //protected $user_repository;
    public function __construct(/* UserRepositoryInterface $userRepositoryInterface */)
    {
        //$this->user_repository=$userRepositoryInterface;
    }

    /**
     * Display a listing of the resource.
     *
     * @return \Illuminate\Http\Response
     */
    public function index()
    {
        //
    }

    /**
     * Show the form for creating a new resource.
     *
     * @return \Illuminate\Http\Response
     */
    public function create()
    {
        //
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
     * @param  \App\Models\Assure  $assure
     * @return \Illuminate\Http\Response
     */
    public function show(Assure $assure)
    {
        //
    }


    /**
     * Display the specified resource.
     *
     * @param  int $assure
     * @return ClientResource
     */
    public function client($assure)
    {
        $client=Client::findOrfail($assure);

        return new ClientResource($client);

    }


    public function marchand($assure)
    {
        $client=Marchand::findOrfail($assure);

        return new MarchandResource($client);

    }

    public function marchandzone()
    {

    }

    public function super_marchand($assure)
    {
        $client=SuperMarchand::findOrfail($assure);

        return new SuperMarchandResource($client);

    }

    /**
     * Show the form for editing the specified resource.
     *
     * @param  \App\Models\Assure  $assure
     * @return \Illuminate\Http\Response
     */
    public function edit(Assure $assure)
    {
        //
    }

    /**
     * Update the specified resource in storage.
     *
     * @param  \Illuminate\Http\Request  $request
     * @param  \App\Models\Assure  $assure
     * @return \Illuminate\Http\Response
     */
    public function update(Request $request, Assure $assure)
    {
        //
    }

    /**
     * Remove the specified resource from storage.
     *
     * @param  \App\Models\Assure  $assure
     * @return \Illuminate\Http\Response
     */
    public function destroy(Assure $assure)
    {
        //
    }
}
