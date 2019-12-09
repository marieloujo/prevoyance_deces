<?php

namespace App\Services\Contract\ServiceInterface;

use App\Http\Requests\User\RegisterRequest;
use Illuminate\Http\Request;
use Illuminate\Http\Response;

interface UserServiceInterface
{
    public function getAuthenticatedUser();
    public function getDiscussions($user);
    public function getNotifications($user);
    public function getAuthenticatedUserable();
    public function getUserByPhone($phone);
    public function getUserByNom($nom);
    public function index();
    public function read($user);
    public function update(Request $registerRequest,$user);
    public function delete($user);
}