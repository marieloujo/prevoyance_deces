<?php

namespace App\Http\Controllers\Api;

use App\Http\Controllers\Controller;
use App\Http\Requests\User\RegisterRequest;
use App\Http\Resources\Collection\ConversationsUserResource;
use App\Http\Resources\Collection\NotificationsUserResource;
use App\Http\Resources\UserResources;
use App\Http\Resources\UsersResource;
use App\Services\Contract\ServiceInterface\UserServiceInterface;
use Illuminate\Http\JsonResponse;
use Illuminate\Http\Request;
use Symfony\Component\HttpFoundation\Response;

class UserController extends Controller
{   
    protected $userService;

    public function __construct(UserServiceInterface $userService)
    {
        $this->userService = $userService;
    }

    public function getAuthenticatedUserable()
    {
        $result = $this->userService->getAuthenticatedUserable();
        return $result;
    }

    public function getDiscussions($user)
    {
        $result = $this->userService->getDiscussions($user);
        if(count($result)>0){
            return ConversationsUserResource::collection($result);
        }else{
            return response()->json([ 'success' => ['message' => 'Aucune discussion' ]], Response::HTTP_NOT_FOUND);
        }
        
    }

    public function getNotifications($user)
    {
        $result = $this->userService->getNotifications($user);
        if(count($result)>0){
            return NotificationsUserResource::collection($result);
        }else{
            return response()->json([ 'success' => ['message' => 'Aucune notification' ]], Response::HTTP_NOT_FOUND);
        }
        
    }

    public function index(){
        $userData=$this->userService->index();
        if(count($userData)>0){
            return UserResources::collection($userData);
        }else{
            return response()->json([ 'success' => ['message' => 'Aucun utilisateur' ]], Response::HTTP_NOT_FOUND);
        }
    }

    public function show($user){
        $userData=$this->userService->read($user);
        if($userData){
            return response()->json([ 'success' => ['data' => new UserResources($userData) ]], Response::HTTP_OK);
        }else{
            return response()->json([ 'success' => ['message' => 'Aucun utilisateur trouvé' ]], Response::HTTP_NOT_FOUND);
        }
    }

    public function readByNom($nom){
        $userData=$this->userService->getUserByNom($nom);
        if(count($userData)>0){
            return UsersResource::collection($userData);
        }else{
            return response()->json([ 'success' => ['message' => 'Aucun utilisateur de ce nom' ]], Response::HTTP_NOT_FOUND);
        }
    }

    public function readByPhone($phone){
        $userData=$this->userService->getUserByPhone($phone);
        if($userData){
            return response()->json([ 'success' => ['data' => new UsersResource($userData) ]], Response::HTTP_OK);
        }else{
            return response()->json([ 'success' => ['message' => 'Aucun utilisateur trouvé' ]], Response::HTTP_NOT_FOUND);
        }
    }

    public function update(Request $registerRequest, $user){
        try {         
            return response()->json([ 'success' => ['data' => new UserResources( $this->userService->update($registerRequest,$user)) ]], Response::HTTP_OK);
        } catch (\Exception $e) {
            $message = $e->getMessage();
            return response()->json(['errors' => [ 'message' => $message]],Response::HTTP_NOT_FOUND);
        }
    }

    public function delete($user){
        try {         
            return response()->json([ 'success' => ['message' => $this->userService->delete($user) ]], Response::HTTP_NO_CONTENT);
        } catch (\Exception $e) {
            $message = $e->getMessage();
            return response()->json(['errors' => [ 'message' => $message]],Response::HTTP_NOT_FOUND);
        }
    }
}
