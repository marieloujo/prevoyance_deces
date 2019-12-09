<?php


namespace App\Services;

use App\Repositories\BeneficiaireRepository;
use App\Services\Contract\AbstractService;
use App\Services\Contract\ServiceInterface\BeneficiaireServiceInterface;
use Illuminate\Http\Request;

class BeneficiaireService extends AbstractService implements BeneficiaireServiceInterface
{
    public function __construct(BeneficiaireRepository $repository)
    {
        parent::__construct($repository);
    }

    public function index()
    {
       return parent::index();
    }
    public function read($beneficiaire)
    {
       return parent::read($beneficiaire);
    }

    public function create(Request $request)
    {
       return parent::create($request);
    }

    public function update(Request $request,$beneficiaire)
    {
       return parent::update($request,$beneficiaire);
    }
    
    public function delete($beneficiaire)
    {
       return parent::delete($beneficiaire);
    }
}