<?php

namespace App\Services\Contract\ServiceInterface;

use Illuminate\Http\Request;
use Illuminate\Http\Response;

interface ConversationUserServiceInterface
{
    public function index();
    public function read($conversationUser);
    public function create(Request $request);
    public function delete($conversationUser);
    public function update(Request $request, $conversationUser);
}