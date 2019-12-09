<?php

namespace App\Services\Contract\ServiceInterface;

use Illuminate\Http\Response;

interface DepartementServiceInterface
{
    public function getCommunes($departement);
    public function index();
    public function read($departement);
}