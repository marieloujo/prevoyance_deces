<?php


namespace App\Services;

use App\Models\Commune;
use App\Repositories\CommuneRepository;
use App\Services\Contract\AbstractService;
use App\Services\Contract\ServiceInterface\CommuneServiceInterface;
use Illuminate\Http\Request;

class CommuneService extends AbstractService implements CommuneServiceInterface
{
    public function __construct(CommuneRepository $repository)
    {
        parent::__construct($repository);
    }
   
    public function getDepartement($commune)
    {
        return $this->read($commune)->departement;        
    }

   
    public function getUsers($commune)
    {
        return $this->read($commune)->users;        
    }

    //return on response json
    public function index()
    {
       return parent::index();
    }

    public function read($commune)
    {
       return parent::read($commune);
    }

}