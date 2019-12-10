<?php

namespace App\Services\Contract\ServiceInterface;

use Illuminate\Http\Request;
use Illuminate\Http\Response;

interface SuperMarchandServiceInterface
{
    public function index();
    public function read($marchand);
    public function create(Request $request);
    public function delete($marchand);
    public function update(Request $request, $marchand);
    public function getMarchands($marchand);
    public function getCompte($marchand);
    public function getComptes($marchand, $end, $start);
}