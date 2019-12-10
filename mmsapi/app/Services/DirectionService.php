<?php


namespace App\Services;

use App\Repositories\DirectionRepository;
use App\Services\Contract\AbstractService;
use App\Services\Contract\ServiceInterface\DirectionServiceInterface;
use Illuminate\Http\Request;

class DirectionService extends AbstractService implements DirectionServiceInterface
{
    public function __construct(DirectionRepository $repository)
    {
        parent::__construct($repository);
    }

    public function index()
    {
       return parent::index();
    }
    public function read($direction)
    {
       return parent::read($direction);
    }

    public function create(Request $request)
    {
       return parent::create($request);
    }

    public function update(Request $request,$direction)
    {
       return parent::update($request,$direction);
    }
    
    public function delete($direction)
    {
       return parent::delete($direction);
    }
}