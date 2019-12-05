<?php

namespace App\Http\Controllers\Api;

use Illuminate\Http\Request;
use App\Http\Controllers\Controller;
use App\Repositories\Assurer\Interfaces\AssurerRepositoryInterface;
use App\Repositories\Benefice\Interfaces\BeneficeRepositoryInterface;
use App\Repositories\Beneficiaire\Interfaces\BeneficiaireRepositoryInterface;
use App\Repositories\Client\Interfaces\ClientRepositoryInterface;
use App\Repositories\Contrat\Interfaces\ContratRepositoryInterface;
use App\Repositories\Document\Interfaces\DocumentRepositoryInterface;
use App\Repositories\Message\Interfaces\MessageRepositoryInterface;
use App\Repositories\Portefeuille\Interfaces\PortefeuilleRepositoryInterface;
use App\Repositories\User\Interfaces\UserRepositoryInterface;
use Carbon\Carbon;
use Illuminate\Http\Resources\Json\JsonResource;
use Illuminate\Http\Response;
use Illuminate\Support\Facades\Auth;
use Illuminate\Support\Facades\DB;

class ContratController extends Controller
{ 
    
    protected $user_repository;
    protected $client_repository;
    protected $assure_repository;
    protected $message_repository;
    protected $contrat_repository;
    protected $benefice_repository;
    protected $document_repository;
    protected $marchand_repository;
    protected $direction_repository;
    protected $portefeuille_repository;
    protected $beneficiaire_repository;
    protected $supermarchand_repository;
    
    public function __construct(ContratRepositoryInterface $contratRepositoryInterface,UserRepositoryInterface $userRepositoryInterface,
         ClientRepositoryInterface $clientRepositoryInterface, AssurerRepositoryInterface $assurerRepositoryInterface,BeneficiaireRepositoryInterface $beneficiaireRepositoryInterface,
         BeneficeRepositoryInterface $beneficeRepositoryInterface,MessageRepositoryInterface $messageRepositoryInterface,DocumentRepositoryInterface $documentRepositoryInterface,
         PortefeuilleRepositoryInterface $portefeuilleRepositoryInterface)
    {
        $this->user_repository=$userRepositoryInterface;    
        $this->client_repository=$clientRepositoryInterface;    
        $this->assure_repository=$assurerRepositoryInterface;   
        $this->contrat_repository=$contratRepositoryInterface;   
        $this->message_repository=$messageRepositoryInterface;  
        $this->benefice_repository=$beneficeRepositoryInterface;  
        $this->beneficiaire_repository=$beneficiaireRepositoryInterface;  
        $this->portefeuille_repository=$portefeuilleRepositoryInterface;  
    }

    /**
     * Display a listing of the resource.
     *
     * @return \Illuminate\Http\Response
     */
    public function index($contrat)
    {
        //return ClientResource($this->contrat_repository->all($contrat));
    }

    /**
     * Store a newly created resource in storage.
     *
     * @param  \Illuminate\Http\Request  $request
     * @return \Illuminate\Http\Response
     */
    public function store(Request $request)
    {
        $client_data=$this->getUsereable($request->all(),'client');
        $beneficiaire_data=$this->getUsereable($request->all(),'beneficiaire');
        $assure_data=$this->getUsereable($request->all(),'assure');
        
        //return response()->json(['data' => $client_data ]);
        //return response()->json(['data' => $beneficiaire_data[0]['statut'] ]);
        // foreach ($beneficiaire_data as $beneficiaire){
        //     return response()->json(['data' => $beneficiaire['beneficiaire'] ]);
        // }
        DB::beginTransaction();
        //return $request;
		try {
            //return response()->json(['data' => $client_data['user'] ]);
            //create client
            //$client=$this->create($this->client_repository,'client',$client_data);
            
            //create assure
            $assure=$this->create($this->assure_repository,'assure',$assure_data);
            return response()->json(['data' => $assure]); 
           
            //create beneficiaire
            /* $beneficiaire=$this->create($this->beneficiaire_repository,'beneficiaire',$beneficiaire_data);
            return response()->json(['data' => $beneficiaire ]); */
            $marchand= $request['marchand'];// $this->getData($this->getData($request->all(),'marchand'),'id');
            return response()->json(['data' => $marchand ]); 
            //$contrat_data = $this->getContratData($request->all(),$client->id,$assure->id,$marchand);
            
           // $contrat=$this->contrat_repository->create($contrat_data);
           //return response()->json(['data' => $contrat ]); 
            foreach ($beneficiaire_data as $beneficiaire){
                $beneficiaire0=$this->create($this->beneficiaire_repository,'beneficiaire',$beneficiaire['beneficiaire']);
                $benefice_data=$beneficiaire[0];
            
                //$benefice_data['contrat_id']=$contrat->id;
                return response()->json(['data' => $beneficiaire0 ]); 
                $benefice_data['beneficiaire_id']=$beneficiaire0->id;
                return response()->json(['data' => $beneficiaire0->id ]); 
                $benefice=$this->benefice_repository->create($benefice_data);
                //return response()->json(['data' => $beneficiaire['beneficiaire'] ]);
            }

            /* $benefice_data=$this->getData($request->all(),'beneficiaire');
           
            $benefice_data['contrat_id']=$contrat->id;

            $benefice_data['beneficiaire_id']=$beneficiaire->id;

            $benefice=$this->benefice_repository->create($benefice_data); */
 
            DB::commit();   
            return response()->json(['data' => $benefice ]);
		} catch (\Exception $e) {
		    DB::rollback();
		    // something went wrong
		    $message = $e->getMessage();
		    return response()->json(['error' => 1, 'message' => $message]);
		}

        
    }

    /**
     * Display the specified resource.
     *
     * @param  int  $id
     * @return \Illuminate\Http\Response
     */
    public function show($id)
    {
        return $this->contrat_repository->getById($id);
    }

    public function showByNumContrat($id)
    {
        return $this->contrat_repository->getByNumeroContrat($id);
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
        //
    }

    /**
     * Remove the specified resource from storage.
     *
     * @param  int  $id
     * @return \Illuminate\Http\Response
     */
    public function destroy($id)
    {
        $this->contrat_repository->delete($id);
        
        //$this->client_images_repository->deleteT($client);

        return response()->json([
            "status" => " success",
            "message" => "Le contrat a bien été supprimer"
        ],Response::HTTP_NO_CONTENT);
    }

    public function create($model_repository,$type,array $request){
        $model=$model_repository->create($request);
        $id=$model['id'];
        $user= $this->createUser($request['user'],$id,$type);
        return $user;
        return $model;
    }

    public function createUser($user_data,$model,$type){
        $user_data['usereable_id']=$model;
        $user_data['commune']=$user_data['commune']['id'];
        $user_data['usereable_type']='App\\Models\\'.strtoupper(ucfirst($type));
        $user= $this->user_repository->register($user_data);
        return $user;
    }

    public function getData(array $request, $url){
        return $request[$url];
    }

    public function getUsereable(array $request, $url){
        return $request[$url];
    }


    public function getContratData(array $request,$client_id,$assure_id,$marchand){
            $contrat['numero_contrat']   = str_random('10')/*  $this->getData($request,'numero_contrat') */;
            $contrat['duree']   = 1 /* $this->getData($request,'duree') */;
            $contrat['garantie']   = 1000000 /* $this->getData($request,'garantie') */;
            $contrat['prime']   = 1000 /* $this->getData($request,'prime') */;
            $contrat['marchand_id']   = $request['marchand']['id'];
            $contrat['date_debut']   = Carbon::now();
            $contrat['date_echeance']   = Carbon::now()->addYear();
            $contrat['date_effet']   = Carbon::now();
            $contrat['date_fin']   = Carbon::now()->addYear();
            $contrat['fin']   = false;
            $contrat['valider']   = false;
            $contrat['client_id']   = $client_id;
            $contrat['assure_id']   = $assure_id;
            $contrat['numero_police_assurance']   = " fgfg";//$this->getData($request,'numero_police_assurance');

        return $contrat;
    }

    

}
