<?php


namespace App\Services;

use App\Repositories\ClientRepository;
use App\Services\Contract\AbstractService;
use App\Services\Contract\ServiceInterface\ClientServiceInterface;
use Illuminate\Http\Request;

class ClientService extends AbstractService implements ClientServiceInterface
{
    public function __construct(ClientRepository $repository)
    {
        parent::__construct($repository);
    }

    public function getContrats($client)
    {
        return $this->read($client)->contrats()->where('valider',true)->orderByDesc('fin',false)->orderByDesc('created_at')->paginate();
    }
    public function getLastContrats($client)
    {
        return $this->read($client)->contrats()->where('valider',true)->where('fin',false)->orderByDesc('created_at')->limit(10)->paginate(5);
    }

    public function index()
    {
       return parent::index();
    }
    public function read($client)
    {
       return parent::read($client);
    }

    public function create(Request $request)
    {
       return parent::create($request);
    }

    public function update(Request $request,$client)
    {
       return parent::update($request,$client);
    }
    
    public function delete($client)
    {
       return parent::delete($client);
    }
}