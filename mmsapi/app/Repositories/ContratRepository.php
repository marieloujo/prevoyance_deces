<?php

namespace App\Repositories;

use App\Models\Contrat;
use App\Repositories\Contracts\AbstractRepository;


class ContratRepository extends AbstractRepository
{
    public function __construct(Contrat $contrat)
    {
        parent::__construct($contrat);
    }
}