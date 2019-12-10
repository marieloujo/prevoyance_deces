<?php

namespace App\Repositories;

use App\Models\Marchand;
use App\Repositories\Contracts\AbstractRepository;


class MarchandRepository extends AbstractRepository
{
    public function __construct(Marchand $marchand)
    {
        parent::__construct($marchand);
    }
}