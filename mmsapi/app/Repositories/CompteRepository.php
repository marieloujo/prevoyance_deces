<?php

namespace App\Repositories;

use App\Models\Compte;
use App\Repositories\Contracts\AbstractRepository;

class CompteRepository extends AbstractRepository
{
    public function __construct(Compte $compte)
    {
        parent::__construct($compte);
    }
}