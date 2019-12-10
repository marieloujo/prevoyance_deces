<?php

namespace App\Http\Controllers\Api;

use App\Http\Resources\SuperMarchand\MarchandResources;
use App\Http\Resources\SuperMarchandsResource;

use App\Http\Resources\ComptesResource;
use App\Services\Contract\ServiceInterface\SuperMarchandServiceInterface;
use App\Http\Controllers\Controller;
use Carbon\Carbon;
use Illuminate\Http\Request;
use Symfony\Component\HttpFoundation\Response;

class SuperMarchandController extends Controller
{

    protected $supermarchandService;

    public function __construct(SuperMarchandServiceInterface $supermarchandService)
    {
        $this->supermarchandService = $supermarchandService;
    } 

    /**
     * Display a listing of the resource.
     *
     * @return \Illuminate\Http\Response
     */
    public function index()
    {
        $supermarchandData=$this->supermarchandService->index();
        if(count($supermarchandData)>0){
            return SuperMarchandsResource::collection($supermarchandData);
        }else{
            return response()->json([ 'success' => ['message' => 'Aucun supermarchand' ]], Response::HTTP_NOT_FOUND);
        }
    }

    /**
     * Display the specified resource.
     *
     * @param  int  $id
     * @return \Illuminate\Http\Response
     */
    public function show($supermarchand)
    {
        $supermarchandsData=$this->supermarchandService->read($supermarchand);
        if($supermarchandsData){
            return response()->json([ 'success' => ['data' => new SuperMarchandsResource($supermarchandsData) ]], Response::HTTP_OK);
        }else{
            return response()->json([ 'success' => ['message' => 'Aucun supermarchand' ]], Response::HTTP_NOT_FOUND);
        }
    }

    public function store(Request $request)
    {
        try {         
            return response()->json([ 'success' => ['data' => new SuperMarchandsResource( $this->supermarchandService->create($request)) ]], Response::HTTP_OK);
        } catch (\Exception $e) {
            $message = $e->getMessage();
            return response()->json(['errors' => [ 'message' => $message]],Response::HTTP_NOT_FOUND);
        }
    }

    /**
     * Update the specified resource in storage.
     *
     * @param  \Illuminate\Http\Request  $request
     * @param  int  $id
     * @return \Illuminate\Http\Response
     */

    public function update(Request $request, $supermarchand){
        
        try {         
            if($this->supermarchandService->update($request,$supermarchand)){
                return $this->show($supermarchand);
            }else{
                return response()->json([ 'errors' => ['message' =>  'Le supermarchand n\'a pas été mis à jour'  ]], Response::HTTP_INTERNAL_SERVER_ERROR);
            }
        } catch (\Exception $e) {
            $message = $e->getMessage();
            return response()->json(['errors' => [ 'message' => $message]],Response::HTTP_INTERNAL_SERVER_ERROR);
        }
    }

    public function destroy($supermarchand){
        try {       
            if($this->supermarchandService->delete($supermarchand)){
                return response()->json([ 'success' => ['message' => 'Le supermarchand à bien été supprimé' ]], Response::HTTP_OK);
            }else{
                return response()->json([ 'errors' => ['message' =>  'Le supermarchand n\'a pas pu être supprimé'  ]], Response::HTTP_INTERNAL_SERVER_ERROR);
            }  
        } catch (\Exception $e) {
            $message = $e->getMessage();
            return response()->json(['errors' => [ 'message' => $message]],Response::HTTP_INTERNAL_SERVER_ERROR);
        }
    }

    public function getCompte($supermarchand){
        $compteData=$this->supermarchandService->getCompte($supermarchand);
        
        if($compteData){
            return response()->json([ 'success' => ['commission' => $compteData ]], Response::HTTP_OK);
        }else{
            return response()->json([ 'success' => ['message' => 'Aucune commission' ]], Response::HTTP_NOT_FOUND);
        }
    }


    public function getComptes($supermarchand,$date=null){
        
        if($date){
            $end=Carbon::parse((Carbon::parse($date))->addDay())->format('Y-m-d');
            $start=Carbon::parse((Carbon::parse($date)->subMonths(3))->addDay())->format('Y-m-d');
            $comptesData=$this->supermarchandService->getComptes($supermarchand,$end,$start);
        }else{
            $end=Carbon::parse((Carbon::now())->addDay())->format('Y-m-d');
            $start=Carbon::parse(((Carbon::now())->subMonths(3))->addDay())->format('Y-m-d');
            $comptesData=$this->supermarchandService->getComptes($supermarchand,$end,$start);
        }
        
        if(count($comptesData)>0){
            return ComptesResource::collection($comptesData);
        }else{
            return response()->json([ 'success' => ['message' => 'Aucun comptes' ]], Response::HTTP_NOT_FOUND);
        }
    }

    public function getMarchands($supermarchand){
        
        $marchandsData=$this->supermarchandService->getMarchands($supermarchand);
        if(count($marchandsData)>0){
            return MarchandResources::collection($marchandsData);
        }else{
            return response()->json([ 'success' => ['message' => 'Aucun marchand' ]], Response::HTTP_NOT_FOUND);
        }
    }

}
