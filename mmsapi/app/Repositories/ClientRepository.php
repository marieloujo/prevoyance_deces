<?php

namespace App\Repositories;

use App\Models\Client;
use App\Repositories\Contracts\AbstractRepository;


class ClientRepository extends AbstractRepository
{
    public function __construct(Client $client)
    {
        parent::__construct($client);
    }
}