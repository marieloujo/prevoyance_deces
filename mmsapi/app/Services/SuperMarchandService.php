<?php


namespace App\Services;

use App\Repositories\SuperMarchandRepository;
use App\Services\Contract\AbstractService;
use App\Services\Contract\ServiceInterface\SuperMarchandServiceInterface;
use Illuminate\Http\Request;

class SuperMarchandService extends AbstractService implements SuperMarchandServiceInterface
{
   public function __construct(SuperMarchandRepository $repository){
        parent::__construct($repository);
    }

   public function getMarchands($superMarchand){
         return $this->read($superMarchand)->marchands()->orderByDesc('created_at')->paginate();
   }

   public function getCompte($superMarchand){
      return $this->read($superMarchand)->comptes()->where('compte','commission')->orderByDesc('created_at')->first();
   }

   public function getComptes($superMarchand, $end, $start){
      return $this->read($superMarchand)->comptes()->where('created_at','<=',$end)->where('created_at','>=',$start)->paginate();
   }

   public function index(){
       return parent::index();
   }
   public function read($superMarchand){
       return parent::read($superMarchand);
   }

   public function create(Request $request){
       return parent::create($request);
   }

   public function update(Request $request,$superMarchand){
       return parent::update($request,$superMarchand);
   }
    
   public function delete($superMarchand){
       return parent::delete($superMarchand);
   } 
}