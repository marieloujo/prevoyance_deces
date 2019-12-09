<?php

namespace App\Http\Controllers\Api;

use App\Http\Controllers\Controller;
use App\Http\Resources\BeneficiairesResource;
use App\Services\Contract\ServiceInterface\BeneficiaireServiceInterface;
use Illuminate\Http\Request;
use Symfony\Component\HttpFoundation\Response;

class BeneficiaireController extends Controller
{
    protected $beneficiaireService;

    public function __construct(BeneficiaireServiceInterface $beneficiaireService)
    {
        $this->beneficiaireService = $beneficiaireService;
    } 

    /**
     * Display a listing of the resource.
     *
     * @return \Illuminate\Http\Response
     */
    public function index()
    {
        return $beneficiaireData=$this->beneficiaireService->index();
        if(count($beneficiaireData)>0){
            return BeneficiairesResource::collection($beneficiaireData);
        }else{
            return response()->json([ 'success' => ['message' => 'Aucun beneficiaire' ]], Response::HTTP_NOT_FOUND);
        }
    }

    /**
     * Display the specified resource.
     *
     * @param  int  $id
     * @return \Illuminate\Http\Response
     */
    public function show($beneficiaire)
    {
        $beneficiaireData=$this->beneficiaireService->read($beneficiaire);
        if($beneficiaireData){
            return response()->json([ 'success' => ['data' => new BeneficiairesResource($beneficiaireData) ]], Response::HTTP_OK);
        }else{
            return response()->json([ 'success' => ['message' => 'Aucun beneficiaire' ]], Response::HTTP_NOT_FOUND);
        }
    }

    public function store(Request $request)
    {
        try {         
            return response()->json([ 'success' => ['data' => new BeneficiairesResource( $this->beneficiaireService->create($request)) ]], Response::HTTP_OK);
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


    public function update(Request $request, $beneficiaire){
        
        try {         
            if($this->beneficiaireService->update($request,$beneficiaire)){
                return $this->show($beneficiaire);
            }else{
                return response()->json([ 'errors' => ['message' =>  'Le beneficiaire n\'a pas été mis à jour'  ]], Response::HTTP_INTERNAL_SERVER_ERROR);
            }
        } catch (\Exception $e) {
            $message = $e->getMessage();
            return response()->json(['errors' => [ 'message' => $message]],Response::HTTP_INTERNAL_SERVER_ERROR);
        }
    }

    public function destroy($beneficiaire){
        try {       
            if($this->beneficiaireService->delete($beneficiaire)){
                return response()->json([ 'success' => ['message' => 'Le beneficiaire à bien été supprimé' ]], Response::HTTP_OK);
            }else{
                return response()->json([ 'errors' => ['message' =>  'Le beneficiaire n\'a pas été supprimé'  ]], Response::HTTP_INTERNAL_SERVER_ERROR);
            }  
        } catch (\Exception $e) {
            $message = $e->getMessage();
            return response()->json(['errors' => [ 'message' => $message]],Response::HTTP_INTERNAL_SERVER_ERROR);
        }
    }
}
