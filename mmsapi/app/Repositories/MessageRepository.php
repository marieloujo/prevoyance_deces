<?php

namespace App\Repositories;

use App\Models\Message;
use App\Repositories\Contracts\AbstractRepository;


class MessageRepository extends AbstractRepository
{
    public function __construct(Message $message)
    {
        parent::__construct($message);
    }
}