<?php


namespace App\Services;

use App\Repositories\BeneficeRepository;
use App\Services\Contract\AbstractService;
use App\Services\Contract\ServiceInterface\BeneficeServiceInterface;
use Illuminate\Http\Request;

class BeneficeService extends AbstractService implements BeneficeServiceInterface
{
    public function __construct(BeneficeRepository $repository)
    {
        parent::__construct($repository);
    }

    public function index()
    {
       return parent::index();
    }
    public function read($benefice)
    {
       return parent::read($benefice);
    }

    public function create(Request $request)
    {
       return parent::create($request);
    }

    public function update(Request $request,$benefice)
    {
       return parent::update($request,$benefice);
    }
    
    public function delete($benefice)
    {
       return parent::delete($benefice);
    }
}