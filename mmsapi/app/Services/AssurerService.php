<?php


namespace App\Services;

use App\Repositories\AssurerRepository;
use App\Services\Contract\AbstractService;
use App\Services\Contract\ServiceInterface\AssurerServiceInterface;
use Illuminate\Http\Request;

class AssurerService extends AbstractService implements AssurerServiceInterface
{
    public function __construct(AssurerRepository $repository)
    {
        parent::__construct($repository);
    }

    public function index()
    {
       return parent::index();
    }
    public function read($assurer)
    {
       return parent::read($assurer);
    }

    public function create(Request $request)
    {
       return parent::create($request);
    }

    public function update(Request $request,$assurer)
    {
       return parent::update($request,$assurer);
    }
    
    public function delete($assurer)
    {
       return parent::delete($assurer);
    }
}