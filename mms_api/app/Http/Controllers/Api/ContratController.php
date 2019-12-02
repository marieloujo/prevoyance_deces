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
use Illuminate\Http\Resources\Json\JsonResource;
use Illuminate\Http\Response;
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
        return ClientResource($this->contrat_repository->all($contrat));
    }

    /**
     * Store a newly created resource in storage.
     *
     * @param  \Illuminate\Http\Request  $request
     * @return \Illuminate\Http\Response
     */
    public function store(Request $request)
    {
        DB::beginTransaction();

		try {
            //create client
            $client=$this->client_repository->create($this->create($this->client_repository,'client',$request->all()));

            /* //create assure
            $assure=$this->create($this->assure_repository,'assure',$request->all());
            
            //create beneficiaire
            $beneficiaire=$this->create($this->beneficiaire_repository,'beneficiaire',$request->all());

            $contrat_data = $this->getContratData($request->all(),$client->id,$assure->id);

            $contrat=$this->contrat_repository->create($contrat_data);

            $benefice_data=$this->getData($request->all(),'beneficiaire');

            $benefice_data['contrat_id']=$contrat->id;

            $benefice_data['beneficiaire_id']=$beneficiaire->id;

            $this->benefice_repository->create($contrat_data);
 */
            return response()->json(['data' => 'ok'
                //$this->create($this->client_repository,'client',$request->all())
            ]) ;

            // $client_data=$request['client'];
            // $user_data=$request['client']['user'];
            // $client=$this->client_repository->create($client_data);
            // $user_data['usereable_id']=$client->id;
            // $user_data['usereable_type']='App\\Models\\Client';
            
            // $user=$this->user_repository->register($user_data);
            
	        
            DB::commit();   
            return response()->json(['data' => $client ]);
		} catch (\Exception $e) {
		    DB::rollback();
		    // something went wrong
		    $message = $e->getMessage();
		    return response()->json(['error' => 1, 'message' => $message]);
		}

        //return $request->all();
        /* return $client=$this->user_repository->create($request->contrats->client->all());
        $request->contrats->client->utilisateur['usereable_id']=$client->id;
        $request->contrats->client->utilisateur['usereable_type']='App\\Models\\Client';  */

        //return $this->user_repository->register($request->contrats->client->utilisateur->all());
        //return $request->contrats->client->utilisateur[nom];
        //$client=ClientController($this->user_repository,$this->client_repository);
        //return $client->store($request->all());
        
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
        $model_data=$this->getData($request,$type);
        // $user_data=$request[$type]['user'];
        // $model=$model_repository->create($model_data);
        // $user_data['usereable_id']=$model->id;
        // $user_data['usereable_type']='App\\Models\\'.ucfirst(strtoupper($type));
        // $this->user_repository->register($user_data);
         return $model_data;
    }

    public function getData(array $request, $url){
        return $request[$url];
    }

    public function getContratData(array $request,$client_id,$assure_id){
            $contrat['numero_contrat         ']   = $this->getData($request,'numero_contrat');
            $contrat['garantie               ']   = $this->getData($request,'garantie');
            $contrat['prime                  ']   = $this->getData($request,'prime');
            $contrat['date_debut             ']   = Carbon::now();
            $contrat['date_echeance          ']   = Carbon::now()->addYear();
            $contrat['date_effet             ']   = Carbon::now();
            $contrat['date_fin               ']   = Carbon::now()->addYear();
            $contrat['fin                    ']   = false;
            $contrat['valider                ']   = false;
            $contrat['client_id              ']   = $client_id;
            $contrat['marchand_id            ']   = $this->getData($request,'marchand_id');
            $contrat['assure_id              ']   = $assure_id;
            $contrat['numero_police_assurance']   = $this->getData($request,'numero_police_assurance');

        return $contrat;
    }


}
