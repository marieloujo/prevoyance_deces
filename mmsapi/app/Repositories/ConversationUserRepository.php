<?php

namespace App\Repositories;

use App\Models\ConversationUser;
use App\Repositories\Contracts\AbstractRepository;


class ConversationUserRepository extends AbstractRepository
{
    public function __construct(ConversationUser $conversationUser)
    {
        parent::__construct($conversationUser);
    }
}