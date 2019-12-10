<?php

namespace App\Repositories;

use App\Models\Direction;
use App\Repositories\Contracts\AbstractRepository;


class DirectionRepository extends AbstractRepository
{
    public function __construct(Direction $direction)
    {
        parent::__construct($direction);
    }
}