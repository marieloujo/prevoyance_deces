<?php


namespace App\Services;

use App\Repositories\DocumentRepository;
use App\Services\Contract\AbstractService;
use App\Services\Contract\ServiceInterface\DocumentServiceInterface;
use Illuminate\Http\Request;

class DocumentService extends AbstractService implements DocumentServiceInterface
{
    public function __construct(DocumentRepository $repository)
    {
        parent::__construct($repository);
    }

    public function index()
    {
       return parent::index();
    }
    public function read($document)
    {
       return parent::read($document);
    }

    public function create(Request $request)
    {
       return parent::create($request);
    }

    public function update(Request $request,$document)
    {
       return parent::update($request,$document);
    }
    
    public function delete($document)
    {
       return parent::delete($document);
    }
}