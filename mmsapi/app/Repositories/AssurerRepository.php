<?php

namespace App\Repositories;

use App\Models\Assurer;
use App\Repositories\Contracts\AbstractRepository;

class AssurerRepository extends AbstractRepository
{
    public function __construct(Assurer $assurer)
    {
        parent::__construct($assurer);
    }
}