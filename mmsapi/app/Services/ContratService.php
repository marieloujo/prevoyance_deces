<?php


namespace App\Services;
use App\Models\Contrat;
use App\Repositories\ContratRepository;
use App\Services\Contract\AbstractService;
use App\Services\Contract\ServiceInterface\ContratServiceInterface;
use Illuminate\Http\Request;

class ContratService extends AbstractService implements ContratServiceInterface
{
    public function __construct(ContratRepository $repository)
    {
        parent::__construct($repository);
    }

    public function index()
    {
       return parent::index();
    }
    public function read($contrat)
    {
       return parent::read($contrat);
    }
    public function readContrat($ref)
    { 
      return Contrat::where('numero_contrat',$ref)->first();
       
    }

    public function create(Request $request)
    {
       return parent::create($request);
    }

    public function update(Request $request,$contrat)
    {
       return parent::update($request,$contrat);
    }
    
    public function delete($contrat)
    {
       return parent::delete($contrat);
    }
}