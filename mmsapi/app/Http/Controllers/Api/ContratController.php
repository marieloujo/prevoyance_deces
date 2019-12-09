<?php

namespace App\Http\Controllers\Api;

use App\Http\Controllers\Controller;
use App\Http\Requests\User\RegisterRequest;
use App\Http\Resources\ContratsResource;
use App\Services\Contract\ServiceInterface\AssurerServiceInterface;
use App\Services\Contract\ServiceInterface\BeneficeServiceInterface;
use App\Services\Contract\ServiceInterface\BeneficiaireServiceInterface;
use App\Services\Contract\ServiceInterface\ClientServiceInterface;
use App\Services\Contract\ServiceInterface\ContratServiceInterface;
use App\Services\Contract\ServiceInterface\DocumentServiceInterface;
use App\Services\Contract\ServiceInterface\PortefeuilleServiceInterface;
use App\Services\Contract\ServiceInterface\RegistrationServiceInterface;

use Illuminate\Support\Str;
use Carbon\Carbon;
use Illuminate\Http\Request;
use Illuminate\Support\Facades\DB;
use Symfony\Component\HttpFoundation\Response;

class ContratController extends Controller
{ 
    protected $contratService;
    protected $clientService;
    protected $beneficeService;
    protected $beneficiaireService;
    protected $portefeuilleService;
    protected $assurerService;
    protected $documentService;
    protected $userService;

    public function __construct(ContratServiceInterface $contratService,ClientServiceInterface $clientServiceInterface,
        BeneficeServiceInterface $beneficeServiceInterface, BeneficiaireServiceInterface $beneficiaireServiceInterface,
        PortefeuilleServiceInterface $portefeuilleServiceInterface, AssurerServiceInterface $assurerServiceInterface,
        DocumentServiceInterface $documentServiceInterface, RegistrationServiceInterface $registrationServiceInterface
    )
    {
        $this->contratService = $contratService;
        $this->clientService = $clientServiceInterface;

        $this->beneficeService = $beneficeServiceInterface;
        $this->beneficiaireService = $beneficiaireServiceInterface;
        $this->assurerService = $assurerServiceInterface;
        $this->portefeuilleService = $portefeuilleServiceInterface;
        $this->documentService = $documentServiceInterface;
        $this->userService = $registrationServiceInterface;
        
        
    } 

    /**
     * Display a listing of the resource.
     *
     * @return \Illuminate\Http\Response
     */
    public function index()
    {
        $contratData=$this->contratService->index();
        if(count($contratData)>0){
            return ContratsResource::collection($contratData);
        }else{
            return response()->json([ 'success' => ['message' => 'Aucun contrat' ]], Response::HTTP_NOT_FOUND);
        }
    }

    /**
     * Display the specified resource.
     *
     * @param  int  $id
     * @return \Illuminate\Http\Response
     */
    public function show($contrat)
    {
        $contratData=$this->contratService->read($contrat);
        if($contratData){
            return response()->json([ 'success' => ['data' => new ContratsResource($contratData) ]], Response::HTTP_OK);
        }else{
            return response()->json([ 'success' => ['message' => 'Aucun contrat' ]], Response::HTTP_NOT_FOUND);
        }
    }

    public function store(Request $request)
    {   
        try {         
            return response()->json([ 'success' => ['data' => new ContratsResource( $this->save($request)) ]], Response::HTTP_OK);
        } catch (\Exception $e) {
            $message = $e->getMessage();
            return response()->json(['errors' => [ 'message' => $message]],Response::HTTP_INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Update the specified resource in storage.
     *
     * @param  \Illuminate\Http\Request  $request
     * @param  int  $id
     * @return \Illuminate\Http\Response
     */


    public function update(Request $request, $contrat){
        
        try {         
            if($this->contratService->update($request,$contrat)){
                return $this->show($contrat);
            }else{
                return response()->json([ 'errors' => ['message' =>  'Le contrat n\'a pas été mis à jour'  ]], Response::HTTP_INTERNAL_SERVER_ERROR);
            }
        } catch (\Exception $e) {
            $message = $e->getMessage();
            return response()->json(['errors' => [ 'message' => $message]],Response::HTTP_INTERNAL_SERVER_ERROR);
        }
    }

    public function destroy($contrat){
        try {       
            if($this->contratService->delete($contrat)){
                return response()->json([ 'success' => ['message' => 'Le contrat à bien été supprimé' ]], Response::HTTP_OK);
            }else{
                return response()->json([ 'errors' => ['message' =>  'Le contrat n\'a pas été supprimé'  ]], Response::HTTP_INTERNAL_SERVER_ERROR);
            }  
        } catch (\Exception $e) {
            $message = $e->getMessage();
            return response()->json(['errors' => [ 'message' => $message]],Response::HTTP_INTERNAL_SERVER_ERROR);
        }
    }

    public function save(Request $request){
        
        DB::beginTransaction();
        
		try {
            $client_data['profession']=$request['client']['profession'];
            $client_data['employeur']=$request['client']['employeur'];
            $client_request = new Request($client_data);
            
            $client=$this->clientService->create($client_request);
            
            $client_user_data=$request['client']['user'];
            
            $client_user_data['userable_id']=$client->id;
            
            $client_user_data['commune']=$client_user_data['commune']['id'];
            $client_user_data['userable_type']='App\\Models\\Client';
            $client_user_request = new Request($client_user_data);
            $user= $this->userService->register($client_user_request);
    
            $assure_data['profession']=$request['assure']['profession'];
            $assure_data['employeur']=$request['assure']['employeur'];
            $assure_data['etat']=false;
            $assure_request = new Request($assure_data);
            $assure=$this->assurerService->create($assure_request);

            $assure_user_data=$request['assure']['user'];
            $assure_user_data['userable_id']=$assure->id;
            $assure_user_data['commune']=$assure_user_data['commune']['id'];
            $assure_user_data['userable_type']='App\\Models\\Assurer';
            $assure_user_request = new Request($assure_user_data);
            $user= $this->userService->register($assure_user_request);
            
            $contrat_data=$this->getContratData($request->all(),$client->id,$assure->id);
            $contrat_request = new Request($contrat_data);
            $contrat=$this->contratService->create($contrat_request);
            
            $benefices_data=$request['benefices'];

            $i=0;
            $benefice=array();

            foreach ($benefices_data as $benefice){
                
                $benefice_data['statut']=$benefice['statut'];
                $benefice_data['taux']=$benefice['taux'];
                $benefice_beneficiaire_data=$benefice['beneficiaire']['user'];

                $benefice_beneficiaire_request = new Request($benefice_beneficiaire_data);

                $beneficiaire=$this->beneficiaireService->create($benefice_beneficiaire_request);

                $beneficiaire_user_data=$benefice_beneficiaire_data;

                $beneficiaire_user_data['userable_id']=$beneficiaire->id;
                $beneficiaire_user_data['commune']=$beneficiaire_user_data['commune']['id'];
                $beneficiaire_user_data['userable_type']='App\\Models\\Beneficiaire';
                $beneficiaire_user_request = new Request($beneficiaire_user_data);
                $user= $this->userService->register($beneficiaire_user_request);

                $benefice_data['beneficiaire_id']=$beneficiaire->id;
                $benefice_data['contrat_id']=$contrat->id;

                $benefice_request = new Request($benefice_data);
                $benefice[$i]=$this->beneficeService->create($benefice_request);
                $i++;
                            
            }
            
            //return response()->json([ 'success' => ['data' => new ContratsResource( $contrat) ]], Response::HTTP_OK);
            //return $contrat;

            DB::commit();   
            return $contrat;
		} catch (\Exception $e) {
		    DB::rollback();
		    // something went wrong
		    $message = $e->getMessage();
		    return response()->json(['error' => 1, 'message' => $message]);
		}

    }



    public function getContratData(array $request,$client_id,$assure_id){
            $contrat['numero_contrat']   = Str::random(10);/*  $this->getData($request,'numero_contrat') */
            $contrat['duree']   = 1 /* $this->getData($request,'duree') */;
            $contrat['garantie']   = 1000000 /* $this->getData($request,'garantie') */;
            $contrat['prime']   = 1000 /* $this->getData($request,'prime') */;
            $contrat['marchand_id']   = $request['marchand']['id'] /*  $this->getData($this->getData($request->all(),'marchand'),'id') */;
            $contrat['date_debut']   = Carbon::now();
            $contrat['date_echeance']   = Carbon::now()->addWeek()->addYear();
            $contrat['date_effet']   = Carbon::now()->addWeek();
            $contrat['date_fin']   = Carbon::now()->addWeek()->addYear();
            $contrat['fin']   = false;
            $contrat['valider']   = false;
            $contrat['client_id']   = $client_id;
            $contrat['assure_id']   = $assure_id;
            $contrat['numero_police_assurance']   = 4245674 ;//$this->getData($request,'numero_police_assurance');

        return $contrat;
    }

}
