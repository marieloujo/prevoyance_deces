<?php

namespace App\Http\Controllers\Api;

use Illuminate\Http\Request;
use App\Http\Controllers\Controller;
use App\Http\Requests\MarchandRequest;
use App\Http\Resources\Marchand\MarchandResource;
use App\Http\Resources\Departement\UsersDepartementResource;
use App\Http\Resources\Marchand\PortefeuilleResource;
use App\Http\Resources\MarchandResources\ClientResources;
use App\Http\Resources\MarchandResources\ContratResources;
use App\Http\Resources\MarchandResources\TransactionResources;
use App\Models\Contrat;
use App\Models\Portefeuille;
use App\Repositories\Marchand\Interfaces\MarchandRepositoryInterface;
use App\Repositories\Client\Interfaces\ClientRepositoryInterface;
use App\Repositories\Portefeuille\Interfaces\PortefeuilleRepositoryInterface;
use App\Repositories\User\Interfaces\UserRepositoryInterface;
use Illuminate\Database\Eloquent\Builder;
use Illuminate\Support\Carbon;
use Illuminate\Support\Facades\Auth;
use Illuminate\Support\Facades\DB;

class MarchandController extends Controller
{
    protected $user_repository;
    protected $portefeuille_repository;
    protected $marchand_repository;
    protected $marchand;
    public function __construct(MarchandRepositoryInterface $marchandRepositoryInterface, UserRepositoryInterface $userRepositoryInterface, PortefeuilleRepositoryInterface $portefeuilleRepositoryInterface)
    {
        $this->user_repository=$userRepositoryInterface;    
        $this->portefeuille_repository=$portefeuilleRepositoryInterface; 
        $this->marchand_repository=$marchandRepositoryInterface;    
    }
/**
     * Display a listing of the resource.
     *
     * @return \Illuminate\Http\Response
     */
    public function index()
    {
        return MarchandResource::collection( $this->marchand_repository->all());
    }

    /**
     * Store a newly created resource in storage.
     *
     * @param  \Illuminate\Http\Request  $request
     * @return \Illuminate\Http\Response
     */
    public function store(MarchandRequest $request)
    {
        return $this->marchand_repository->create($request->all());
    }

    /**
     * Display the specified resource.
     *
     * @param  int  $id
     * @return \Illuminate\Http\Response
     */
    public function show($id)
    {
        return $this->marchand_repository->getById($id);
    }

    /**
     * Display the specified resource.
     *
     * @param  int  $id
     * @return \Illuminate\Http\Response
     */
    public function showByCommune()
    {
        return new UsersDepartementResource($this->user_repository->getAuth()->commune->departement);
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
        return $this->marchand_repository->update($id,$request->all());
    }

    /**
     * Remove the specified resource from storage.
     *
     * @param  int  $id
     * @return \Illuminate\Http\Response
     */
    public function destroy($id)
    {
        return $this->marchand_repository->delete($id);
    }

    public function getContrats($marchand,$client)
    {
        //return $this->marchand_repository->getContrats($marchand,$client);
        return ContratResources::collection($this->marchand_repository->getContrats($marchand,$client)) ;
        return new MarchandResource($this->marchand_repository->getById($marchand));
    }



    public function getClients($marchand)
    {  
        return ClientResources::collection($this->marchand_repository->getClients($marchand));
    }

    public function getTransaction($marchand,$date=null)
    { 
        $this->marchand=$marchand;
        
        if(!$date){

            $start=Carbon::parse((Carbon::now()->subWeek())->addDay())->format('Y-m-d');
            $end=Carbon::parse((Carbon::now())->addDay())->format('Y-m-d');

            $portefeuilles=Portefeuille::whereHas('contrat', function(Builder $query){
                $query->where('marchand_id',$this->marchand);
            })->where('created_at','<=',$end)
            ->where('created_at','>=',$start)->orderByDesc('created_at')->paginate();

        }else{
            $start=Carbon::parse((Carbon::parse($date)->subMonths(3))->addDay())->format('Y-m-d');
            $end=Carbon::parse((Carbon::parse($date))->addDay())->format('Y-m-d');
            
            $portefeuilles=Portefeuille::whereHas('contrat', function(Builder $query){
                $query->where('marchand_id',$this->marchand);
            })->where('created_at','<=',$end)
            ->where('created_at','>=',$start)->orderByDesc('created_at')->paginate();

        }
        return TransactionResources::collection($portefeuilles);
    }
    public function getTransactions($marchand)
    {   
        $this->marchand=$marchand;
        $contrat=Portefeuille::whereHas('contrat', function(Builder $query){
            $query->where('marchand_id',$this->marchand);
        })->orderByDesc('created_at')->paginate();

        return TransactionResources::collection($contrat);

    }
}
