<?php


namespace App\Services;

use App\Repositories\ConversationUserRepository;
use App\Services\Contract\AbstractService;
use App\Services\Contract\ServiceInterface\ConversationUserServiceInterface;
use Illuminate\Http\Request;

class ConversationUserService extends AbstractService implements ConversationUserServiceInterface
{
    public function __construct(ConversationUserRepository $repository)
    {
        parent::__construct($repository);
    }

    public function index()
    {
       return parent::index();
    }
    public function read($conversationUser)
    {
       return parent::read($conversationUser);
    }

    public function create(Request $request)
    {
       return parent::create($request);
    }

    public function update(Request $request,$conversationUser)
    {
       return parent::update($request,$conversationUser);
    }
    
    public function delete($conversationUser)
    {
       return parent::delete($conversationUser);
    }
}