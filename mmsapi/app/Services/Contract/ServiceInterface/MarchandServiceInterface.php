<?php

namespace App\Services\Contract\ServiceInterface;

use Illuminate\Http\Request;
use Illuminate\Http\Response;

interface MarchandServiceInterface
{
    public function index();
    public function read($marchand);
    public function create(Request $request);
    public function delete($marchand);
    public function update(Request $request, $marchand);
    public function getClients($marchand);
    public function getProspects($marchand);
    public function getTransactions($marchand);
    public function getCompte($marchand);
    public function getComptes($marchand, $end, $start);
    public function getLastTransactions($marchand, $end, $start);
    public function getContrats($marchand,$client);
}