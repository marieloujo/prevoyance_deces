<?php


namespace App\Services;

use App\Repositories\CompteRepository;
use App\Services\Contract\AbstractService;
use App\Services\Contract\ServiceInterface\CompteServiceInterface;
use Illuminate\Http\Request;

class CompteService extends AbstractService implements CompteServiceInterface
{
    public function __construct(CompteRepository $repository)
    {
        parent::__construct($repository);
    }

    public function index()
    {
       return parent::index();
    }
    public function read($compte)
    {
       return parent::read($compte);
    }

    public function create(Request $request)
    {
       return parent::create($request);
    }

    public function update(Request $request,$compte)
    {
       return parent::update($request,$compte);
    }
    
    public function delete($compte)
    {
       return parent::delete($compte);
    }
}