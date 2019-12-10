<?php


namespace App\Services;

use App\Repositories\MessageRepository;
use App\Services\Contract\AbstractService;
use App\Services\Contract\ServiceInterface\MessageServiceInterface;
use Illuminate\Http\Request;

class MessageService extends AbstractService implements MessageServiceInterface
{
    public function __construct(MessageRepository $repository)
    {
        parent::__construct($repository);
    }

    public function index()
    {
       return parent::index();
    }
    public function read($message)
    {
       return parent::read($message);
    }

    public function create(Request $request)
    {
       return parent::create($request);
    }

    public function update(Request $request,$message)
    {
       return parent::update($request,$message);
    }
    
    public function delete($message)
    {
       return parent::delete($message);
    }
}