<?php


namespace App\Services;

use App\Http\Resources\UsersResource;
use App\Models\Departement;
use App\Repositories\DepartementRepository;
use App\Services\Contract\AbstractService;
use App\Services\Contract\ServiceInterface\DepartementServiceInterface;
use Illuminate\Http\Request;
use Illuminate\Support\Facades\Auth;

class DepartementService extends AbstractService implements DepartementServiceInterface
{
    public function __construct(DepartementRepository $repository)
    {
        parent::__construct($repository);
    }

   
    public function getCommunes($departement)
    {
        return $this->read($departement)->communes()->paginate();        
    }

    public function index()
    {
       return parent::index();
    }

    public function read($departement)
    {
       return parent::read($departement);
    }

}