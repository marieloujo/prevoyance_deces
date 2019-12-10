<?php

namespace App\Repositories;

use App\Models\Commune;
use App\Repositories\Contracts\AbstractRepository;


class CommuneRepository extends AbstractRepository
{
    public function __construct(Commune $commune)
    {
        parent::__construct($commune);
    }
}