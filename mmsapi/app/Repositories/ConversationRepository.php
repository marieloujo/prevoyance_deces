<?php

namespace App\Repositories;

use App\Models\Conversation;
use App\Repositories\Contracts\AbstractRepository;


class ConversationRepository extends AbstractRepository
{
    public function __construct(Conversation $conversation)
    {
        parent::__construct($conversation);
    }
}