<?php


namespace App\Services;

use App\Repositories\PortefeuilleRepository;
use App\Services\Contract\AbstractService;
use App\Services\Contract\ServiceInterface\PortefeuilleServiceInterface;
use Illuminate\Http\Request;

class PortefeuilleService extends AbstractService implements PortefeuilleServiceInterface
{
    public function __construct(PortefeuilleRepository $repository)
    {
        parent::__construct($repository);
    }

    public function index()
    {
       return parent::index();
    }
    public function read($portefeuille)
    {
       return parent::read($portefeuille);
    }

    public function create(Request $request)
    {
       return parent::create($request);
    }

    public function update(Request $request,$portefeuille)
    {
       return parent::update($request,$portefeuille);
    }
    
    public function delete($portefeuille)
    {
       return parent::delete($portefeuille);
    }
}