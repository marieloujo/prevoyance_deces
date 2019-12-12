<?php


namespace App\Services;

use App\Repositories\ConversationRepository;
use App\Services\Contract\AbstractService;
use App\Services\Contract\ServiceInterface\ConversationServiceInterface;
use Illuminate\Http\Request;

class ConversationService extends AbstractService implements ConversationServiceInterface
{
    public function __construct(ConversationRepository $repository)
    {
        parent::__construct($repository);
    }

    public function index()
    {
       return parent::index();
    }
    public function read($conversation)
    {
       return parent::read($conversation);
    }

    public function create(Request $request)
    {
       return parent::create($request);
    }

    public function update(Request $request,$conversation)
    {
       return parent::update($request,$conversation);
    }
    
    public function delete($conversation)
    {
       return parent::delete($conversation);
    }
}