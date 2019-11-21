<?php

namespace App\Http\Controllers;

use App\Http\Resources\Assurer\AssurerResource;
use App\Http\Resources\Client\ClientResource;
use App\Http\Resources\SuperMarchandResources\SuperMarchandResource as AppSuperMarchandResource;
use App\Http\Resources\MarchandResources\MarchandResource as AppMarchandResource;
use App\Http\Resources\ClientResources\ClientResource as AppClientResource;
use App\Models\Assure;
use App\Models\Client;
use App\Models\Marchand;
use App\Models\Portefeuille;
use App\Models\SuperMarchand;
use Carbon\Carbon;
use Illuminate\Http\Request;

class ModelsClientController extends Controller
{
    /**
     * Display a listing of the resource.
     *
     * @return \Illuminate\Http\Response
     */
    public function index()
    {   $marchand=SuperMarchand::find(2);
         
        return new AppSuperMarchandResource($marchand);

        $client=Client::find(3);
       return new AppClientResource($client);
        

        //$assure=Assure::find(14);
       // $superMarchand=SuperMarchand::find(1);
        //return new AssurerResource($assure);
        //return new ClientResource($client);

        // $portefeuille=new Portefeuille;
        // $portefeuille->date_payement=Carbon::now();
        // $portefeuille->assurance_id=6;

        // $portefeuille->save();

        // return $portefeuille;




        $date=Carbon::parse('2019-1-12');

        $return='vous avez souscrit à votre assurance le '.$date->format('d-m-y');
        $now=Carbon::now();

        $last=$date->addMonths(22);
        $return.=' votre dernier paiement remonte au  '.$last->format('d-m-y');
        $diff=$now->diffForHumans($last);
        $return.=' vous avez '.$diff;
        $now->diffForHumans();
        if ($diff) {
            return $diff;
        }
        return $return;'vous avez souscrit à votre assurance le '.$date->format('d-m-y').' vous avez solder votre compte de '.$last->format('d-m-y');
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
     * @param  \App\ModelsClient  $modelsClient
     * @return \Illuminate\Http\Response
     */
    public function show(ModelsClient $modelsClient)
    {
        //
    }

    /**
     * Show the form for editing the specified resource.
     *
     * @param  \App\ModelsClient  $modelsClient
     * @return \Illuminate\Http\Response
     */
    public function edit(ModelsClient $modelsClient)
    {
        //
    }

    /**
     * Update the specified resource in storage.
     *
     * @param  \Illuminate\Http\Request  $request
     * @param  \App\ModelsClient  $modelsClient
     * @return \Illuminate\Http\Response
     */
    public function update(Request $request, ModelsClient $modelsClient)
    {
        //
    }

    /**
     * Remove the specified resource from storage.
     *
     * @param  \App\ModelsClient  $modelsClient
     * @return \Illuminate\Http\Response
     */
    public function destroy(ModelsClient $modelsClient)
    {
        //
    }
}
