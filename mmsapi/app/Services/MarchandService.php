<?php


namespace App\Services;

use App\Repositories\MarchandRepository;
use App\Services\Contract\AbstractService;
use App\Services\Contract\ServiceInterface\MarchandServiceInterface;
use Illuminate\Http\Request;

class MarchandService extends AbstractService implements MarchandServiceInterface
{
   public function __construct(MarchandRepository $repository){
        parent::__construct($repository);
    }

   public function getLastTransactions($marchand, $end, $start){
      return  $this->read($marchand)->portefeuilles()->where('created_at','<=',$end)->where('created_at','>=',$start)->orderByDesc('created_at')->paginate();
    }

   public function getClients($marchand){
         return $this->read($marchand)->contrats()->where('valider',true)->distinct('client_id')->orderByDesc('created_at')->paginate();
   }

   public function getProspects($marchand){
        return $this->read($marchand)->prospects()->where('prospect',true)->orderByDesc('created_at')->paginate();
   }

   public function getTransactions($marchand){
      return  $this->read($marchand)->portefeuilles()->orderByDesc('created_at')->paginate();
   }

   public function getCompte($marchand){
      return $this->read($marchand)->comptes()->where('compte','credit_virtuel')->orderByDesc('created_at')->first();
   }

   public function getComptes($marchand, $end, $start){
      return $this->read($marchand)->comptes()->where('created_at','<=',$end)->where('created_at','>=',$start)->paginate();
   }

   public function index(){
       return parent::index();
   }
   public function read($marchand){
       return parent::read($marchand);
   }

   public function create(Request $request){
       return parent::create($request);
   }

   public function update(Request $request,$marchand){
       return parent::update($request,$marchand);
   }
    
   public function delete($marchand){
       return parent::delete($marchand);
   }

   public function getContrats($marchand,$client){
        return $this->read($marchand)->contrats()->where('client_id',$client)->where('valider',true)->orderByDesc('fin',false)->orderByDesc('created_at')->paginate();
   } 
}